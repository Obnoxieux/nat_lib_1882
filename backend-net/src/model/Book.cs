namespace backend_net.model;

public class Book
{
    public int Id { get; set; }
    public string Title { get; set; }
    public Author[] Authors { get; set; }
    public Genre Genre { get; set; }
    public Endowment? Endowment { get; set; }
    public int Number { get; set; }
    public bool Manuscript { get; set; }
    public bool Print { get; set; }
    public string? Comment { get; set; }
    public string? EditorComment { get; set; }
    public string? Volume { get; set; }
}