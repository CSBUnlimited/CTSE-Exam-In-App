using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Exam
{
    public class ExamRequest : BaseRequest
    {
        public Models.Exam Exam { get; set; }
    }
}
