using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CTSE_Exam_In_App.DataAccess;
using CTSE_Exam_In_App.DTOs.Base;
using CTSE_Exam_In_App.DTOs.Question;
using CTSE_Exam_In_App.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class QuestionController : ControllerBase
    {
        private readonly ExamInAppDbContext _dbContext;

        public QuestionController(ExamInAppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpGet("{examId}", Name = "GetQuestionsByExamIdAsync")]
        public async Task<IActionResult> GetQuestionsByExamIdAsync(int examId)
        {
            QuestionResponse questionResponse = new QuestionResponse();

            try
            {
                questionResponse.Questions = await _dbContext.Questions
                    .Include(q => q.Answers)
                    .Where(q => q.ExamId == examId)
                    .ToListAsync();

                questionResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                questionResponse.Message = ex.Message;
            }

            return StatusCode(questionResponse.Status, questionResponse);
        }

        [HttpGet("{id}", Name = "GetQuestionByIdAsync")]
        public async Task<IActionResult> GetQuestionByIdAsync(int id)
        {
            QuestionResponse questionResponse = new QuestionResponse();

            try
            {
                questionResponse.Questions = await _dbContext.Questions
                    .Include(q => q.Answers)
                    .Where(q => q.Id == id)
                    .ToListAsync();

                questionResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                questionResponse.Message = ex.Message;
            }

            return StatusCode(questionResponse.Status, questionResponse);
        }

        [HttpPost(Name = "AddQuestionAsync")]
        public async Task<IActionResult> AddQuestionAsync([FromBody]QuestionRequest questionRequest)
        {
            QuestionResponse questionResponse = new QuestionResponse();

            try
            {
                await questionRequest.GetRequestedUserAsync(_dbContext);
                Question question = questionRequest?.Question;

                if (question == null)
                {
                    throw new Exception("Question is empty");
                }

                await _dbContext.Questions.AddAsync(question);
                await _dbContext.SaveChangesAsync();

                Question addedQuestion = await _dbContext.Questions
                    .Include(q => q.Answers)
                    .FirstAsync(q => q.Id == question.Id);

                Exam exam = await _dbContext.Exams.FindAsync(addedQuestion.ExamId);

                exam.Marks += addedQuestion.Marks;
                await _dbContext.SaveChangesAsync();

                questionResponse.Questions = new List<Question>()
                {
                    addedQuestion
                };

                questionResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                questionResponse.Message = ex.Message;
            }

            return StatusCode(questionResponse.Status, questionResponse);
        }

        [HttpPut(Name = "UpdateQuestionAsync")]
        public async Task<IActionResult> UpdateQuestionAsync([FromBody]QuestionRequest questionRequest)
        {
            QuestionResponse questionResponse = new QuestionResponse();

            try
            {
                await questionRequest.GetRequestedUserAsync(_dbContext);
                Question updatedQuestion = questionRequest?.Question;

                if (updatedQuestion == null)
                {
                    throw new Exception("Question is empty");
                }

                Question question = await _dbContext.Questions.FirstAsync(q => q.Id == updatedQuestion.Id);

                question.Description = updatedQuestion.Description;
                question.Marks = updatedQuestion.Marks;
                await _dbContext.SaveChangesAsync();

                Exam exam = await _dbContext.Exams.FindAsync(question.ExamId);

                exam.Marks += question.Marks;
                await _dbContext.SaveChangesAsync();

                questionResponse.Questions = await _dbContext.Questions
                    .Include(q => q.Answers)
                    .Where(q => q.Id == question.Id)
                    .ToListAsync();

                questionResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                questionResponse.Message = ex.Message;
            }

            return StatusCode(questionResponse.Status, questionResponse);
        }

        [HttpPut("{id}", Name = "DeleteQuestionByIdAsync")]
        public async Task<IActionResult> DeleteQuestionByIdAsync(int id, [FromBody]BaseRequest baseRequest)
        {
            BaseResponse baseResponse = new BaseResponse();

            try
            {
                await baseRequest.GetRequestedUserAsync(_dbContext);

                Question question = await _dbContext.Questions.FirstAsync(q => q.Id == id);

                if (question == null)
                {
                    throw new Exception("Question not found");
                }

                _dbContext.Questions.Remove(question);
                await _dbContext.SaveChangesAsync();

                Exam exam = await _dbContext.Exams.FindAsync(question.ExamId);

                exam.Marks -= question.Marks;
                await _dbContext.SaveChangesAsync();

                baseResponse.Message = "Question removed";
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