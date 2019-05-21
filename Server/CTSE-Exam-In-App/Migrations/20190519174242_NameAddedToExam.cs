using Microsoft.EntityFrameworkCore.Migrations;

namespace CTSE_Exam_In_App.Migrations
{
    public partial class NameAddedToExam : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Name",
                table: "Exams",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Name",
                table: "Exams");
        }
    }
}
