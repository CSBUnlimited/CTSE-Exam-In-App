using Microsoft.EntityFrameworkCore.Migrations;

namespace CTSE_Exam_In_App.Migrations
{
    public partial class MarksAddedToQuestionAndExam : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<float>(
                name: "Marks",
                table: "Questions",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "Marks",
                table: "Exams",
                nullable: false,
                defaultValue: 0f);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Marks",
                table: "Questions");

            migrationBuilder.DropColumn(
                name: "Marks",
                table: "Exams");
        }
    }
}
