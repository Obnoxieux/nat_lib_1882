using backend_net.model;

namespace backend_net.db;

using Microsoft.EntityFrameworkCore;

public class GenreDb : DbContext
{
    public GenreDb(DbContextOptions<GenreDb> options) : base(options)
    {
    }

    public DbSet<Genre> Genres => Set<Genre>();
}