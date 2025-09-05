-- all books aggregated

SELECT
  b.id,
  b.title,
  b.number,
  b.volume,
  b.genre,
  g.name AS genre_name,
  b.endowment,
  e.name AS endowment_name,
  b.manuscript,
  b.print,
  b.comment,
  b.editor_comment,
  COALESCE(
    jsonb_agg(
      jsonb_build_object('id', a.id, 'name', a.name)
      ORDER BY a.name
    ) FILTER (WHERE a.id IS NOT NULL),
    '[]'::jsonb
  ) AS authors
FROM book b
LEFT JOIN genre g      ON g.id = b.genre
LEFT JOIN endowment e  ON e.id = b.endowment
LEFT JOIN authors_books ab ON ab.book_id = b.id
LEFT JOIN author a      ON a.id = ab.author_id
GROUP BY
  b.id, b.title, b.number, b.volume, b.genre, g.name,
  b.endowment, e.name, b.manuscript, b.print, b.comment, b.editor_comment
ORDER BY b.id;
