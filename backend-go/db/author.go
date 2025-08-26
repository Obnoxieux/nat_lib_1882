package db

import "sync"

type Author struct {
	Id   int64  `db:"id"`
	Name string `db:"name"`
}

type AuthorRepository interface {
	GetSingleAuthorByID(id int64) Author
}

type TestAuthorRepository struct {
	Lock sync.Mutex
}

func (repo *TestAuthorRepository) GetSingleAuthorByID(id int64) Author {
	repo.Lock.Lock()
	defer repo.Lock.Unlock()

	return Author{
		Id:   9999,
		Name: "الأصفهاني، أبو الفرج",
	}
}
