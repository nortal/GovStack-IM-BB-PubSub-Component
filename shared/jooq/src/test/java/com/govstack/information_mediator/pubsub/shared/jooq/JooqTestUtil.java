package com.govstack.information_mediator.pubsub.shared.jooq;

import org.jooq.impl.UpdatableRecordImpl;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.Arrays;
import java.util.Collection;

class JooqTestUtil {
    private static final String JOOQ_RECORDS_PACKAGE = "com.govstack.information_mediator.pubsub.shared.jooq.tables.records";

    private JooqTestUtil() {
        throw new AssertionError("Static utility class should not be instantiated");
    }

    @SuppressWarnings("rawtypes")
    static Collection<Class<? extends UpdatableRecordImpl>> retrieveUpdatableRecord() {
        return retrieveUpdatableRecord(UpdatableRecordImpl.class);
    }

    @SuppressWarnings("rawtypes")
    static Collection<Class<? extends UpdatableRecordImpl>> retrieveUpdatableRecord(Class<?>... ignoredClasses) {
        return retrieveClasses(JOOQ_RECORDS_PACKAGE, UpdatableRecordImpl.class, ignoredClasses);
    }

    static <T> Collection<Class<? extends T>> retrieveClasses(String packageName, Class<T> type, Class<?>... ignoredClasses) {
        return new Reflections(packageName, Scanners.SubTypes)
            .getSubTypesOf(type)
            .stream()
            .filter(clazz -> !clazz.getSimpleName().equals(type.getSimpleName()))
            .filter(clazz -> {
                    if (ignoredClasses == null) return true;
                    return Arrays.stream(ignoredClasses)
                        .noneMatch(ignoredClass -> clazz.getSimpleName().equals(ignoredClass.getSimpleName()));
                }
            ).toList();
    }

    static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create new instance for: " + clazz.getName(), e);
        }
    }
}
