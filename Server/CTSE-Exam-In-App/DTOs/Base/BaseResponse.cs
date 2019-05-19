using System;
using System.Net;

namespace CTSE_Exam_In_App.DTOs.Base
{
    public class BaseResponse
    {
        public bool IsSuccess { get; set; }

        public int Status => IsSuccess ? (int)HttpStatusCode.OK : (int)HttpStatusCode.BadRequest;

        public string Message { get; set; }
        public DateTime RespondDateTime { get; set; }
        
        public BaseResponse()
        {
            RespondDateTime = DateTime.Now;
        }
    }
}
