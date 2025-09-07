using backend_net.db;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<GenreDb>(options => options.UseInMemoryDatabase("GenreDb"));
builder.Services.AddDatabaseDeveloperPageExceptionFilter();

var app = builder.Build();

// Configure the HTTP request pipeline.

app.UseHttpsRedirection();

app.MapGet("/", () => "Evil Microsoft API available.");

app.MapGet("/genres", async (GenreDb db) =>
{
    await db.Genres.ToListAsync();
});

app.Run();