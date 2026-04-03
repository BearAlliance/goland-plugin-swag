api-docs:
	go install github.com/swaggo/swag/v2/cmd/swag@v2.0.0-rc5
	swag init --v3.1 -g ./examples/main.go -d .
	mv docs/docs.go examples/docs/docs.go
	mv docs/swagger.json examples/docs/swagger.json
	mv docs/swagger.yaml examples/docs/swagger.yaml
