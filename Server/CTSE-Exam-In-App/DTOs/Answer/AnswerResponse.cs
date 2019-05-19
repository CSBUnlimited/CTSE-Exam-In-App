using System.Collections.Generic;
using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Answer
{
    public class AnswerResponse : BaseResponse
    {
        public IEnumerable<Models.Answer> Answers { get; set; }
    }
}
