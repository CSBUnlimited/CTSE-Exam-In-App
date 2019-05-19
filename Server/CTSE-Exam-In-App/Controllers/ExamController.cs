using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CTSE_Exam_In_App.DataAccess;
using CTSE_Exam_In_App.DTOs.Base;
using CTSE_Exam_In_App.DTOs.Exam;
using CTSE_Exam_In_App.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class ExamController : ControllerBase
    {
        private readonly ExamInAppDbContext _dbContext;

        public ExamController(ExamInAppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpGet(Name = "GetAllExamsAsync")]
        public async Task<IActionResult> GetAllExamsAsync()
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                examResponse.Exams = await _dbContext.Exams
                    .Include(e => e.LecturerUser)
                    .ToListAsync();

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpGet("{isPublished}", Name = "GetAllExamsByIsPublishedAsync")]
        public async Task<IActionResult> GetAllExamsByIsPublishedAsync(bool isPublished)
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                examResponse.Exams = await _dbContext.Exams
                    .Include(e => e.LecturerUser)
                    .Where(e => e.IsPublish == isPublished)
                    .ToListAsync();

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpGet("{lecturerUserId}", Name = "GetExamsByLectureIdAsync")]
        public async Task<IActionResult> GetExamsByLectureIdAsync(int lecturerUserId)
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                examResponse.Exams = await _dbContext.Exams
                    .Include(e => e.LecturerUser)
                    .Where(e => e.LecturerUserId == lecturerUserId)
                    .ToListAsync();

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpGet("{id}", Name = "GetExamByIdAsync")]
        public async Task<IActionResult> GetExamByIdAsync(int id)
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                examResponse.Exams = new List<Exam>()
                {
                    await _dbContext.Exams
                        .Include(e => e.LecturerUser)
                        .Include(e => e.Questions)
                        .SingleOrDefaultAsync(e => e.Id == id)
                };

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpPost(Name = "AddExamAsync")]
        public async Task<IActionResult> AddExamAsync([FromBody]ExamRequest examRequest)
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                User user = await examRequest.GetRequestedUserAsync(_dbContext);
                Exam exam = examRequest.Exam;
                exam.Marks = 0F;
                exam.LecturerUserId = user.Id;

                if (exam.Questions != null)
                {
                    foreach (Question q in exam.Questions)
                    {
                        if (q == null)
                        {
                            continue;
                        }

                        exam.Marks += q.Marks;
                    }
                }

                await _dbContext.Exams.AddAsync(exam);
                await _dbContext.SaveChangesAsync();

                examResponse.Exams = new List<Exam>()
                {
                    await _dbContext.Exams
                        .Include(e => e.LecturerUser)
                        .Include(e => e.Questions)
                        .SingleOrDefaultAsync(e => e.Id == exam.Id)
                };

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpPut(Name = "UpdateExamAsync")]
        public async Task<IActionResult> UpdateExamAsync([FromBody]ExamRequest examRequest)
        {
            ExamResponse examResponse = new ExamResponse();

            try
            {
                User user = await examRequest.GetRequestedUserAsync(_dbContext);
                Exam updatedExam = examRequest.Exam;
                updatedExam.LecturerUserId = user.Id;

                Exam exam = await _dbContext.Exams
                    .FirstOrDefaultAsync(e => e.Id == updatedExam.Id);

                if (exam == null)
                {
                    throw new Exception("Exam not found");
                }

                exam.Description = updatedExam.Description;
                exam.GivenTimeSeconds = updatedExam.GivenTimeSeconds;
                exam.IsPublish = updatedExam.IsPublish;
                exam.EffectiveDateTime = updatedExam.EffectiveDateTime;
                exam.ExpireDateTime = updatedExam.ExpireDateTime;

                await _dbContext.SaveChangesAsync();

                examResponse.Exams = new List<Exam>()
                {
                    await _dbContext.Exams
                        .Include(e => e.LecturerUser)
                        .Include(e => e.Questions)
                        .SingleOrDefaultAsync(e => e.Id == exam.Id)
                };

                examResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                examResponse.Message = ex.Message;
            }

            return StatusCode(examResponse.Status, examResponse);
        }

        [HttpPut("{id}", Name = "DeleteExamByIdAsync")]
        public async Task<IActionResult> DeleteExamByIdAsync(int id, [FromBody]BaseRequest baseRequest)
        {
            BaseResponse baseResponse = new BaseResponse();

            try
            {
                await baseRequest.GetRequestedUserAsync(_dbContext);

                Exam exam = await _dbContext.Exams
                    .Include(e => e.StudentExams)
                    .Include(e => e.Questions)
                        .ThenInclude(q => q.Answers)
                        .ThenInclude(a => a.StudentQuestionAnswers)
                    .SingleOrDefaultAsync(e => e.Id == id);

                if (exam == null)
                {
                    throw new Exception("Exam not found");
                }

                _dbContext.Exams.Remove(exam);
                await _dbContext.SaveChangesAsync();

                baseResponse.Message = "Exam and data deleted";
                baseResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                baseResponse.Message = ex.Message;
            }

            return StatusCode(baseResponse.Status, baseResponse);
        }
    }
}