package api

import (
	"context"

	"github.com/Obnoxieux/nat_lib_1882/db"
	"github.com/pocketbase/dbx"
)

var _ StrictServerInterface = (*Server)(nil)

type Server struct {
	AuthorRepository db.AuthorRepository
}

func NewServer(database *dbx.DB) Server {
	return Server{
		AuthorRepository: db.PostgresAuthorRepository{DB: database},
	}
}

// GetAuthors List all authors
// (GET /authors)
func (s Server) GetAuthors(ctx context.Context, request GetAuthorsRequestObject) (GetAuthorsResponseObject, error) {
	authors, err := s.AuthorRepository.GetAllAuthors()
	if err != nil {
		return nil, err
	}
	var response []Author
	for _, author := range authors {
		response = append(response, Author{Id: author.Id, Name: author.Name})
	}
	return GetAuthors200JSONResponse{
		Items:  response,
		Limit:  0,
		Offset: 0,
		Total:  len(response),
	}, nil
}

// GetAuthorById Get a specific author
// (GET /authors/{id})
func (s Server) GetAuthorById(ctx context.Context, request GetAuthorByIdRequestObject) (GetAuthorByIdResponseObject, error) {
	author, err := s.AuthorRepository.GetSingleAuthorByID(request.Id)
	if err != nil {
		return nil, err
	}
	response := Author{Id: author.Id, Name: author.Name}

	return GetAuthorById200JSONResponse(response), nil
}

// GetBooksByAuthor Get books by an author
// (GET /authors/{id}/books)
func (s Server) GetBooksByAuthor(ctx context.Context, request GetBooksByAuthorRequestObject) (GetBooksByAuthorResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetBooks List all books
// (GET /books)
func (s Server) GetBooks(ctx context.Context, request GetBooksRequestObject) (GetBooksResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetBookById Get a specific book
// (GET /books/{id})
func (s Server) GetBookById(ctx context.Context, request GetBookByIdRequestObject) (GetBookByIdResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetEndowments List all endowments
// (GET /endowments)
func (s Server) GetEndowments(ctx context.Context, request GetEndowmentsRequestObject) (GetEndowmentsResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetEndowmentById Get a specific endowment
// (GET /endowments/{id})
func (s Server) GetEndowmentById(ctx context.Context, request GetEndowmentByIdRequestObject) (GetEndowmentByIdResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetBooksByEndowment Get books by endowment
// (GET /endowments/{id}/books)
func (s Server) GetBooksByEndowment(ctx context.Context, request GetBooksByEndowmentRequestObject) (GetBooksByEndowmentResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetGenres List all genres
// (GET /genres)
func (s Server) GetGenres(ctx context.Context, request GetGenresRequestObject) (GetGenresResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetGenreById Get a specific genre
// (GET /genres/{id})
func (s Server) GetGenreById(ctx context.Context, request GetGenreByIdRequestObject) (GetGenreByIdResponseObject, error) {
	panic("not implemented") // TODO: Implement
}

// GetBooksByGenre Get books by genre
// (GET /genres/{id}/books)
func (s Server) GetBooksByGenre(ctx context.Context, request GetBooksByGenreRequestObject) (GetBooksByGenreResponseObject, error) {
	panic("not implemented") // TODO: Implement
}
