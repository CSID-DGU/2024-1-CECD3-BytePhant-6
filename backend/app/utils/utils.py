from langchain_text_splitters import CharacterTextSplitter

def split_text(documents):
    text_splitter = CharacterTextSplitter(
        separator="\n",
        chunk_size=10,
        chunk_overlap=0,
        length_function=len,
        add_start_index=True,
    )
    return text_splitter.split_documents(documents)