using System;
using System.Threading.Tasks;
using CTSE_Exam_In_App.DataAccess;
using CTSE_Exam_In_App.Models;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.DTOs.Base
{
    public class BaseRequest
    {
        public DateTime RequestedDateTime { get; }
        public string Username { get; set; }
        public Guid? SessionId { get; set; }

        public BaseRequest()
        {
            RequestedDateTime = DateTime.Now;
        }

        public async Task<User> GetRequestedUserAsync(ExamInAppDbContext dbContext)
        {
            User user = await dbContext.Users.FirstOrDefaultAsync(u => u.Username == Username && u.SessionId == SessionId);
            
            if (user == null)
            {
                throw new Exception("Request permission denied");
            }

            return user;
        }
    }
}
