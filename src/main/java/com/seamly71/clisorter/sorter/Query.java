package com.seamly71.clisorter.sorter;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
public class Query {
    public final List<Path> targetFilePaths;
    public final String filePrefix;
    public final Path outputDirectory;
    public final OutputWritePolicy outputWritePolicy;
    public final SummaryVerbosity summaryVerbosity;

    public enum OutputWritePolicy {
        REWRITE,
        APPEND
    }

    public enum SummaryVerbosity {
        NO,
        SHORT,
        FULL
    }

    private Query(Builder builder) {
        this.targetFilePaths = builder.targetFilePaths;
        this.filePrefix = builder.filePrefix;
        this.outputDirectory = builder.outputDirectory;
        this.outputWritePolicy = builder.outputWritePolicy;
        this.summaryVerbosity = builder.summaryVerbosity;
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private List<Path> targetFilePaths = new ArrayList<Path>();
        private String filePrefix = "";
        private Path outputDirectory = Paths.get("").toAbsolutePath();
        private OutputWritePolicy outputWritePolicy = OutputWritePolicy.REWRITE;
        private SummaryVerbosity summaryVerbosity = SummaryVerbosity.NO;

        public Builder() {}

        public Query build() {
            return new Query(this);
        }

        public Builder addTargetFilePath(Path path) {
            this.targetFilePaths.add(path);
            return this;
        }
    }
}
