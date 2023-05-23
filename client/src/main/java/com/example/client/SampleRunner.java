package com.example.client;

import com.apollographql.apollo3.rx3.Rx3Apollo;
import com.example.BooksQuery;
import okhttp3.Response;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.apollographql.apollo3.ApolloClient;

import java.util.List;


@Component
public class SampleRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

        var builder = new ApolloClient.Builder();
        try (ApolloClient apolloClient = builder.serverUrl("http://localhost:4000/graphql/endpoint").build()) {
            var apolloQueryCall = apolloClient.query(new BooksQuery());

            List<BooksQuery.Book> books = Rx3Apollo.from(apolloQueryCall)
                    .map( response -> response.data.books)
                    .blockingFirst();

            System.out.println(books);

        }

    }

}
