package org.example.coworking.factory;

import java.io.IOException;

/**
 * Интерфейс для общей фабрики, создающей экземпляры заданного типа.
 * @param <T> тип объекта, который будет создаваться фабрикой.
 */
public interface CoworkingFactory<T> {

    /**
     * Создает и возвращает новый экземпляр заданного типа.
     *
     * @return новый экземпляр заданного типа T.
     */
    T create() throws IOException;
}
