CREATE TABLE public.url (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    original_url VARCHAR(255) NOT NULL,
    shortened_path VARCHAR(255) NOT NULL,
    shortened_url VARCHAR(255) NOT NULL
    );