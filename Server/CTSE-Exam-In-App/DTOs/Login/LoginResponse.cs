using CTSE_Exam_In_App.DTOs.Base;
using CTSE_Exam_In_App.Models;

namespace CTSE_Exam_In_App.DTOs.Login
{
    public class LoginResponse : BaseResponse
    {
        public User User { get; set; }
    }
}
