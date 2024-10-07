from langchain_community.document_loaders import TextLoader
from langchain_chroma import Chroma
from langchain_community.retrievers import BM25Retriever
from langchain.retrievers import EnsembleRetriever
from langchain_openai import OpenAIEmbeddings
from utils.utils import split_text


async def query_ensemble(query_text, data_path):
    document_loader = TextLoader(data_path, encoding='UTF8')
    pages = document_loader.load()
    docs = split_text(pages)

    embeddings = OpenAIEmbeddings()

    bm25_retriever = BM25Retriever.from_documents(docs)
    bm25_retriever.k = 2

    chroma_vectorstore = Chroma.from_documents(docs, embeddings, persist_directory="./db/chroma")
    chroma_retriever = chroma_vectorstore.as_retriever(search_kwargs={'k': 2})

    ensemble_retriever = EnsembleRetriever(
        retrievers=[bm25_retriever, chroma_retriever],
        weights=[0.5, 0.5],
    )

    results = ensemble_retriever.invoke(query_text)
    return results  


def save_chunks_to_file(chunks, file_path):
    with open(file_path, 'a', encoding='utf-8') as file:
        for chunk in chunks:
            content = chunk.page_content
            file.write(content + '\n')  