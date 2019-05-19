using System.Collections.Generic;
using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Exam
{
    public class ExamResponse : BaseResponse
    {
        public IEnumerable<Models.Exam> Exams { get; set; }
    }
}
