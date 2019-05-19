using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CTSE_Exam_In_App.DataAccess;
using CTSE_Exam_In_App.DTOs.Answer;
using CTSE_Exam_In_App.DTOs.Base;
using CTSE_Exam_In_App.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class AnswerController : ControllerBase
    {
        private readonly ExamInAppDbContext _dbContext;

        public AnswerController(ExamInAppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpGet("{questionId}", Name = "GetAnswersByQuestionIdAsync")]
        public async Task<IActionResult> GetAnswersByQuestionIdAsync(int questionId)
        {
            AnswerResponse answerResponse = new AnswerResponse();

            try
            {
                answerResponse.Answers = await _dbContext.Answers
                    .Where(a => a.QuestionId == questionId)
                    .ToListAsync();

                answerResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                answerResponse.Message = ex.Message;
            }

            return StatusCode(answerResponse.Status, answerResponse);
        }

        [HttpGet("{id}", Name = "GetAnswerByIdAsync")]
        public async Task<IActionResult> GetAnswerByIdAsync(int id)
        {
            AnswerResponse answerResponse = new AnswerResponse();

            try
            {
                answerResponse.Answers = new List<Answer>()
                {
                    await _dbContext.Answers.FindAsync(id)
                };

                answerResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                answerResponse.Message = ex.Message;
            }

            return StatusCode(answerResponse.Status, answerResponse);
        }

        [HttpPost(Name = "AddAnswerAsync")]
        public async Task<IActionResult> AddAnswerAsync([FromBody]AnswerRequest answerRequest)
        {
            AnswerResponse answerResponse = new AnswerResponse();

            try
            {
                await answerRequest.GetRequestedUserAsync(_dbContext);
                Answer answer = answerRequest?.Answer;

                if (answer == null)
                {
                    throw new Exception("Answer is empty");
                }

                await _dbContext.Answers.AddAsync(answer);
                await _dbContext.SaveChangesAsync();

                answerResponse.Answers = new List<Answer>()
                {
                    await _dbContext.Answers.FindAsync(answer.Id)
                };

                answerResponse.Message = "Answer added";
                answerResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                answerResponse.Message = ex.Message;
            }

            return StatusCode(answerResponse.Status, answerResponse);
        }

        [HttpPut(Name = "UpdateAnswerAsync")]
        public async Task<IActionResult> UpdateAnswerAsync([FromBody]AnswerRequest answerRequest)
        {
            AnswerResponse answerResponse = new AnswerResponse();

            try
            {
                await answerRequest.GetRequestedUserAsync(_dbContext);
                Answer updatedAnswer = answerRequest?.Answer;

                if (updatedAnswer == null)
                {
                    throw new Exception("Answer is empty");
                }

                Answer answer = await _dbContext.Answers.FindAsync(updatedAnswer.Id);

                answer.Description = updatedAnswer.Description;
                answer.IsValid = updatedAnswer.IsValid;
                await _dbContext.SaveChangesAsync();

                answerResponse.Answers = new List<Answer>()
                {
                    answer
                };

                answerResponse.Message = "Answer updated";
                answerResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                answerResponse.Message = ex.Message;
            }

            return StatusCode(answerResponse.Status, answerResponse);
        }

        [HttpPut("{id}", Name = "DeleteAnswerByIdAsync")]
        public async Task<IActionResult> DeleteAnswerByIdAsync(int id, [FromBody]BaseRequest baseRequest)
        {
            BaseResponse baseResponse = new BaseResponse();

            try
            {
                await baseRequest.GetRequestedUserAsync(_dbContext);

                Answer answer = await _dbContext.Answers.FindAsync(id);

                if (answer == null)
                {
                    throw new Exception("Answer not found");
                }

                _dbContext.Answers.Remove(answer);
                await _dbContext.SaveChangesAsync();

                baseResponse.Message = "Answer removed";
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