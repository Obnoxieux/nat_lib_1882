package db

import (
	"database/sql"
	"encoding/json"
	"fmt"
)

type AuthorEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

// AuthorsJSON is a scannable slice for the aggregated JSONB column used.
type AuthorsJSON []AuthorEntity

func (a *AuthorsJSON) Scan(src any) error {
	if src == nil {
		*a = AuthorsJSON{}
		return nil
	}
	var b []byte
	switch v := src.(type) {
	case []byte:
		b = v
	case string:
		b = []byte(v)
	default:
		return fmt.Errorf("AuthorsJSON.Scan: unsupported type %T", src)
	}
	return json.Unmarshal(b, (*[]AuthorEntity)(a))
}

type BookEntity struct {
	Id            int64          `db:"id"`
	Title         string         `db:"title"`
	Number        int64          `db:"number"`
	Volume        sql.NullString `db:"volume"`
	Authors       AuthorsJSON    `db:"authors"`
	GenreId       int64          `db:"genre_id"`
	GenreName     string         `db:"genre_name"`
	EndowmentId   sql.NullInt64  `db:"endowment_id"`
	EndowmentName string         `db:"endowment_name"`
	Manuscript    bool           `db:"manuscript"`
	Print         bool           `db:"print"`
	Comment       sql.NullString `db:"comment"`
	EditorComment sql.NullString `db:"editor_comment"`
}

type GenreEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type EndowmentEntity struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}
