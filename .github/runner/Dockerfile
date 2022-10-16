FROM debian:latest

ARG TOKEN=not-set
ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y curl libgtk-dotnet3.0-cil

ENV RUNNER_ALLOW_RUNASROOT=1

RUN mkdir actions-runner && cd actions-runner
RUN curl -o actions-runner-linux-arm64-2.297.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.297.0/actions-runner-linux-arm64-2.297.0.tar.gz && \
	tar xzf ./actions-runner-linux-arm64-2.297.0.tar.gz
RUN ./config.sh --url https://github.com/qnocks/link-shortener --token $TOKEN

CMD ["./run.sh"]