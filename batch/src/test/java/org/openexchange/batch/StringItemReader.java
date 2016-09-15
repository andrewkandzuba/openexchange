package org.openexchange.batch;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StringItemReader implements ItemReader<String>, ItemStream {
    private static final String CURRENT_INDEX = "current.index";
    private final List<String> strings = new CopyOnWriteArrayList<>();
    private volatile int currentIndex = 0;

    @Override
    public String read() throws Exception {
        if (currentIndex < strings.size()) {
            return strings.get(currentIndex++);
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey(CURRENT_INDEX)) {
            currentIndex = executionContext.getInt(CURRENT_INDEX);
        } else {
            currentIndex = 0;
            strings.add("A");
            strings.add("B");
            strings.add("C");
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // NOP
    }

    @Override
    public void close() throws ItemStreamException {
       // NOP
    }
}
