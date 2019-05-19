using Microsoft.EntityFrameworkCore.Migrations;

namespace CTSE_Exam_In_App.Migrations
{
    public partial class IsPublishAddedToExam : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "IsPublish",
                table: "Exams",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "IsPublish",
                table: "Exams");
        }
    }
}
