package api

import "github.com/Obnoxieux/nat_lib_1882/db"

func bookEntityToModel(entity db.BookEntity) Book {
	var authors []Author
	for _, author := range entity.Authors {
		authors = append(authors, Author{Id: author.Id, Name: author.Name})
	}

	return Book{
		Id:      entity.Id,
		Title:   entity.Title,
		Number:  entity.Number,
		Volume:  entity.Volume.String,
		Authors: authors,
		Genre: Genre{
			Id:   entity.GenreId,
			Name: entity.GenreName,
		},
		Endowment: Endowment{
			Id:   entity.EndowmentId.Int64,
			Name: entity.EndowmentName,
		},
		Manuscript:    entity.Manuscript,
		Print:         entity.Print,
		Comment:       entity.Comment.String,
		EditorComment: entity.EditorComment.String,
	}
}

func (ro GetBooksRequestObject) toDBFilter() db.BooksFilter {
	return db.BooksFilter{
		Author:     ro.Params.Author,
		Genre:      ro.Params.Genre,
		Endowment:  ro.Params.Endowment,
		Manuscript: ro.Params.Manuscript,
		Print:      ro.Params.Print,
		Search:     ro.Params.Search,
		Limit:      int64(ro.Params.Limit),
		Offset:     int64(ro.Params.Offset),
	}
}
