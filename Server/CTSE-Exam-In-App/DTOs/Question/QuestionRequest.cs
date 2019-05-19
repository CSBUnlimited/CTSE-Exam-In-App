using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Question
{
    public class QuestionRequest : BaseRequest
    {
        public Models.Question Question { get; set; }
    }
}
