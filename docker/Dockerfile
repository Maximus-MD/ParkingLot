# Stage 1: Builder
FROM alpine:latest AS builder

# Declare build arguments
ARG GROUP_ID
ARG ARTIFACT_ID
ARG VERSION
ARG Nexus_REPO_USER
ARG Nexus_REPO_PASS
ARG Nexus_REPO_URL
ARG REPO_TYPE
ARG UPSTREAM_COMMIT_SHA

# Set environment variables (optional)
ENV GROUP_ID=$GROUP_ID
ENV ARTIFACT_ID=$ARTIFACT_ID
ENV VERSION=$VERSION
ENV Nexus_REPO_USER=$Nexus_REPO_USER
ENV Nexus_REPO_PASS=$Nexus_REPO_PASS
ENV Nexus_REPO_URL=$Nexus_REPO_URL
ENV REPO_TYPE=$REPO_TYPE
ENV UPSTREAM_COMMIT_SHA=$UPSTREAM_COMMIT_SHA

# Use build arguments (optional)
RUN echo "Building $GROUP_ID / $ARTIFACT_ID version $VERSION"

# Install necessary tools
RUN apk add --no-cache wget

# Set environment variables
ENV ARTIFACT_PATH=/repository/maven-${REPO_TYPE}/${GROUP_ID//.//}/${ARTIFACT_ID}/${ARTIFACT_ID}-${VERSION}-${UPSTREAM_COMMIT_SHA}.jar

# Download the artifact
WORKDIR /workspace
RUN wget --user=${Nexus_REPO_USER} --password=${Nexus_REPO_PASS} "${Nexus_REPO_URL}${ARTIFACT_PATH}" -O app.jar

# Stage 2: Final Runtime
FROM eclipse-temurin:17-jre-alpine

# Copy the artifact from the builder stage
COPY --from=builder /workspace/app.jar /app/app.jar

# Set execution permissions and default command
WORKDIR /app
CMD ["java", "-jar", "app.jar"]
