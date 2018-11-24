ALTER TABLE tbl_name
    [ADD [COLUMN] col_name data_type [NOT NULL | NULL] [UNIQUE [KEY]] [COMMENT 'string']
    | ALTER [COLUMN] col_name data_type [NOT NULL | NULL] [UNIQUE [KEY]] [COMMENT 'string']
    | ALTER INDEX index_name data_type [NOT NULL | NULL] [UNIQUE [KEY]] [COMMENT 'string']
    | DROP [COLUMN] col_name data_type [NOT NULL | NULL] [UNIQUE [KEY]] [COMMENT 'string']
    | DROP INDEX index_name data_type [NOT NULL | NULL] [UNIQUE [KEY]] [COMMENT 'string']
]