using System;
using System.Linq;
using System.Threading.Tasks;
using CTSE_Exam_In_App.DataAccess;
using CTSE_Exam_In_App.DTOs.Base;
using CTSE_Exam_In_App.DTOs.Login;
using CTSE_Exam_In_App.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly ExamInAppDbContext _dbContext;

        public LoginController(ExamInAppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        [HttpPost(Name = "LoginAsync")]
        public async Task<IActionResult> LoginAsync([FromBody]LoginRequest loginRequest)
        {
            LoginResponse loginResponse = new LoginResponse();

            try
            {
                User user = await _dbContext.Users
                    .Include(u => u.UserPassword)
                    .Where(u => u.Username.Equals(loginRequest.Username, StringComparison.InvariantCultureIgnoreCase))
                    .FirstOrDefaultAsync();

                if (user?.UserPassword == null || user.UserPassword.Password != loginRequest.Password)
                {
                    throw new Exception("Invalid username or password");
                }

                user.SessionId = Guid.NewGuid();
                await _dbContext.SaveChangesAsync();

                user.UserPassword = null;

                loginResponse.User = user;
                loginResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                loginResponse.Message = ex.Message;
            }

            return StatusCode(loginResponse.Status, loginResponse);
        }

        [HttpPost(Name = "LogoutAsync")]
        public async Task<IActionResult> LogoutAsync([FromBody]BaseRequest baseRequest)
        {
            BaseResponse baseResponse = new BaseResponse();

            try
            {
                User user = await baseRequest.GetRequestedUserAsync(_dbContext);

                user.SessionId = null;
                await _dbContext.SaveChangesAsync();

                baseResponse.Message = "Logged out successfully";
                baseResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                baseResponse.Message = ex.Message;
            }

            return StatusCode(baseResponse.Status, baseResponse);
        }

        [HttpGet("{username},{sessionId}", Name = "GetLoggedInUserByUsernameAndSessionIdAsync")]
        public async Task<IActionResult> GetLoggedInUserByUsernameAndSessionIdAsync(string username, Guid sessionId)
        {
            LoginResponse loginResponse = new LoginResponse();

            try
            {
                User user = await _dbContext.Users
                    .Where(u => u.Username.Equals(username, StringComparison.InvariantCultureIgnoreCase))
                    .FirstOrDefaultAsync();

                if (user?.SessionId == null || user.SessionId != sessionId)
                {
                    throw new Exception("Logged in user session not found");
                }

                user.UserPassword = null;

                loginResponse.User = user;
                loginResponse.IsSuccess = true;
            }
            catch (Exception ex)
            {
                loginResponse.Message = ex.Message;
            }

            return StatusCode(loginResponse.Status, loginResponse);
        }
    }
}
