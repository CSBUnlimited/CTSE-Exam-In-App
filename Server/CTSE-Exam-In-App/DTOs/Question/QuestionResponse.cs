using System.Collections.Generic;
using CTSE_Exam_In_App.DTOs.Base;

namespace CTSE_Exam_In_App.DTOs.Question
{
    public class QuestionResponse : BaseResponse
    {
        public IEnumerable<Models.Question> Questions { get; set; }
    }
}
