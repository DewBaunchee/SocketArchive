package by.varyvoda.matvey.server.http.end_point;

public interface EndPoint<Argument, Result> {
    Result invoke(Argument httpRequest) throws Exception;
}
