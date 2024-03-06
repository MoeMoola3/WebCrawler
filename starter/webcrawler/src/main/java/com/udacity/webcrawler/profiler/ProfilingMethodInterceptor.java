package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final Object delegate;
  private final Clock clock;
  private final ZonedDateTime startTime;
  private final ProfilingState state;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Object delegate, Clock clock, ZonedDateTime startTime, ProfilingState state) {
    this.delegate = delegate;
    this.clock = Objects.requireNonNull(clock);
    this.startTime = startTime;
    this.state = state;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
  // TODO: This method interceptor should inspect the called method to see if it is a profiled
    Instant startTime = null;
    boolean hasProfiledAnnotation = method.getAnnotation(Profiled.class) != null;

    try {
      if (hasProfiledAnnotation) {
        startTime = clock.instant();
      }
      Object result = method.invoke(this.delegate, args);
      if (hasProfiledAnnotation) {
        Duration duration = Duration.between(startTime, clock.instant());
        state.record(this.delegate.getClass(), method, duration);
      }
      return result;
    } catch (InvocationTargetException ex) {
      Throwable targetException = ex.getTargetException();

      if (hasProfiledAnnotation) {
        Duration duration = Duration.between(startTime, clock.instant());
        state.record(this.delegate.getClass(), method, duration);
      }
      throw targetException;
    }
  }
}