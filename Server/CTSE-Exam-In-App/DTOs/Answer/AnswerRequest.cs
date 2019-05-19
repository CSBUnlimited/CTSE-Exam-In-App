using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Answer
{
    public class AnswerRequest : BaseRequest
    {
        public Models.Answer Answer { get; set; }
    }
}
