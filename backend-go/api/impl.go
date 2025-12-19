package api

import (
	"context"

	"github.com/Obnoxieux/nat_lib_1882/db"
	"github.com/pocketbase/dbx"
)

var _ StrictServerInterface = (*Server)(nil)

const DefaultLimit = 100

type Server struct {
	AuthorRepository    db.AuthorRepository
	EndowmentRepository db.EndowmentRepository
	GenreRepository     db.GenreRepository
	BookRepository      db.BookRepository
}

func NewServer(database *dbx.DB) Server {
	return Server{
		AuthorRepository:    db.PostgresAuthorRepository{DB: database},
		EndowmentRepository: db.PostgresEndowmentRepository{DB: database},
		GenreRepository:     db.PostgresGenreRepository{DB: database},
		BookRepository:      db.PostgresBookRepository{DB: database},
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
	booksRequest := GetBooksRequestObject{
		Params: GetBooksParams{
			Author: request.Id,
			Limit:  request.Params.Limit,
			Offset: request.Params.Offset,
		},
	}
	books, err := s.BookRepository.GetFilteredBooks(booksRequest.toDBFilter())
	if err != nil {
		return nil, err
	}
	var response []Book
	for _, book := range books {
		response = append(response, bookEntityToModel(book))
	}
	return GetBooksByAuthor200JSONResponse{
		Items:  response,
		Limit:  request.Params.Limit,
		Offset: request.Params.Offset,
		Total:  len(response),
	}, nil
}

// GetBooks List all books
// (GET /books)
func (s Server) GetBooks(ctx context.Context, request GetBooksRequestObject) (GetBooksResponseObject, error) {
	if request.Params.Limit == 0 {
		request.Params.Limit = DefaultLimit
	}

	books, err := s.BookRepository.GetFilteredBooks(request.toDBFilter())
	if err != nil {
		return nil, err
	}
	var response []Book
	for _, book := range books {
		response = append(response, bookEntityToModel(book))
	}
	return GetBooks200JSONResponse{
		Items:  response,
		Limit:  request.Params.Limit,
		Offset: request.Params.Offset,
		Total:  len(response),
	}, nil
}

// GetBookById Get a specific book
// (GET /books/{id})
func (s Server) GetBookById(ctx context.Context, request GetBookByIdRequestObject) (GetBookByIdResponseObject, error) {
	book, err := s.BookRepository.GetSingleBookByID(request.Id)
	if err != nil {
		return nil, err
	}
	response := bookEntityToModel(book)
	return GetBookById200JSONResponse(response), nil
}

// GetEndowments List all endowments
// (GET /endowments)
func (s Server) GetEndowments(ctx context.Context, request GetEndowmentsRequestObject) (GetEndowmentsResponseObject, error) {
	endowments, err := s.EndowmentRepository.GetAllEndowments()
	if err != nil {
		return nil, err
	}
	var response []Endowment
	for _, e := range endowments {
		response = append(response, Endowment{Id: e.Id, Name: e.Name})
	}
	return GetEndowments200JSONResponse(response), nil
}

// GetEndowmentById Get a specific endowment
// (GET /endowments/{id})
func (s Server) GetEndowmentById(ctx context.Context, request GetEndowmentByIdRequestObject) (GetEndowmentByIdResponseObject, error) {
	endowment, err := s.EndowmentRepository.GetSingleEndowmentByID(request.Id)
	if err != nil {
		return nil, err
	}
	response := Endowment{Id: endowment.Id, Name: endowment.Name}
	return GetEndowmentById200JSONResponse(response), nil
}

// GetBooksByEndowment Get books by endowment
// (GET /endowments/{id}/books)
func (s Server) GetBooksByEndowment(ctx context.Context, request GetBooksByEndowmentRequestObject) (GetBooksByEndowmentResponseObject, error) {
	booksRequest := GetBooksRequestObject{
		Params: GetBooksParams{
			Endowment: request.Id,
			Limit:     request.Params.Limit,
			Offset:    request.Params.Offset,
		},
	}
	books, err := s.BookRepository.GetFilteredBooks(booksRequest.toDBFilter())
	if err != nil {
		return nil, err
	}
	var response []Book
	for _, book := range books {
		response = append(response, bookEntityToModel(book))
	}
	return GetBooksByEndowment200JSONResponse{
		Items:  response,
		Limit:  request.Params.Limit,
		Offset: request.Params.Offset,
		Total:  len(response),
	}, nil
}

// GetGenres List all genres
// (GET /genres)
func (s Server) GetGenres(ctx context.Context, request GetGenresRequestObject) (GetGenresResponseObject, error) {
	genres, err := s.GenreRepository.GetAllGenres()
	if err != nil {
		return nil, err
	}
	var response []Genre
	for _, g := range genres {
		response = append(response, Genre{Id: g.Id, Name: g.Name})
	}
	return GetGenres200JSONResponse(response), nil
}

// GetGenreById Get a specific genre
// (GET /genres/{id})
func (s Server) GetGenreById(ctx context.Context, request GetGenreByIdRequestObject) (GetGenreByIdResponseObject, error) {
	genre, err := s.GenreRepository.GetSingleGenreByID(request.Id)
	if err != nil {
		return nil, err
	}
	response := Genre{Id: genre.Id, Name: genre.Name}
	return GetGenreById200JSONResponse(response), nil
}

// GetBooksByGenre Get books by genre
// (GET /genres/{id}/books)
func (s Server) GetBooksByGenre(ctx context.Context, request GetBooksByGenreRequestObject) (GetBooksByGenreResponseObject, error) {
	booksRequest := GetBooksRequestObject{
		Params: GetBooksParams{
			Genre:  request.Id,
			Limit:  request.Params.Limit,
			Offset: request.Params.Offset,
		},
	}
	books, err := s.BookRepository.GetFilteredBooks(booksRequest.toDBFilter())
	if err != nil {
		return nil, err
	}
	var response []Book
	for _, book := range books {
		response = append(response, bookEntityToModel(book))
	}
	return GetBooksByGenre200JSONResponse{
		Items:  response,
		Limit:  request.Params.Limit,
		Offset: request.Params.Offset,
		Total:  len(response),
	}, nil
}
