package ai.optidash.support;

import ai.optidash.Dynamic;
import ai.optidash.OperationConfiguration;
import ai.optidash.Operations;

public abstract class BaseOperations<T extends Operations> implements Operations<T>{

    protected abstract Dynamic metadata();

    @Override
    public T optimize(OperationConfiguration configuration) {
        metadata().set("optimize",configuration.asMap());
        return (T) this;
    }

    @Override
    public T flip(OperationConfiguration configuration) {
        metadata().set("flip",configuration.asMap());
        return (T) this;
    }

    @Override
    public T resize(OperationConfiguration configuration) {
        metadata().set("resize",configuration.asMap());
        return (T) this;
    }

    @Override
    public T scale(OperationConfiguration configuration) {
        metadata().set("scale",configuration.asMap());
        return (T) this;
    }

    @Override
    public T crop(OperationConfiguration configuration) {
        metadata().set("crop",configuration.asMap());
        return (T) this;
    }

    @Override
    public T watermark(OperationConfiguration configuration) {
        metadata().set("watermark",configuration.asMap());
        return (T) this;
    }

    @Override
    public T mask(OperationConfiguration configuration) {
        metadata().set("mask",configuration.asMap());
        return (T) this;
    }

    @Override
    public T stylize(OperationConfiguration configuration) {
        metadata().set("stylize",configuration.asMap());
        return (T) this;
    }

    @Override
    public T adjust(OperationConfiguration configuration) {
        metadata().set("adjust",configuration.asMap());
        return (T) this;
    }

    @Override
    public T auto(OperationConfiguration configuration) {
        metadata().set("auto",configuration.asMap());
        return (T) this;
    }

    @Override
    public T border(OperationConfiguration configuration) {
        metadata().set("auto",configuration.asMap());
        return (T) this;
    }

    @Override
    public T padding(OperationConfiguration configuration) {
        metadata().set("padding",configuration.asMap());
        return (T) this;
    }

    @Override
    public T store(OperationConfiguration configuration) {
        metadata().set("store",configuration.asMap());
        return (T) this;
    }

    @Override
    public T output(OperationConfiguration configuration) {
        metadata().set("output",configuration.asMap());
        return (T) this;
    }

    @Override
    public T webhook(OperationConfiguration configuration) {
        metadata().set("webhook",configuration.asMap());
        return (T) this;
    }

    @Override
    public T cdn(OperationConfiguration configuration) {
        metadata().set("cdn",configuration.asMap());
        return (T) this;
    }
}
