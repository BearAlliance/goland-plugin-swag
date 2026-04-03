package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

// @title           Example API
// @version         1.0
// @description     A simple example API to demonstrate swag comments.
// @host            localhost:8080
// @BasePath        /api/v1

func main() {
	r := gin.Default()

	v1 := r.Group("/api/v1")
	{
		v1.GET("/health", getHealth)
		v1.GET("/greeting/:name", getGreeting)
	}

	r.Run(":8080")
}

// getHealth returns the health status of the service.
//
// @Summary      Health check
// @Description  Returns OK if the service is running
// @Tags         system
// @Produce      json
// @Success      200  {object}  map[string]string
// @Router       /health [get]
func getHealth(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"status": "ok",
	})
}

// GreetingResponse represents a greeting message.
type GreetingResponse struct {
	Message string `json:"message" example:"Hello, Alice!"`
}

// getGreeting returns a personalized greeting.
//
// @Summary      Greet a user
// @Description  Returns a greeting message for the given name
// @Tags         greetings
// @Produce      json
// @Param        name  path      string  true  "Name of the person to greet"
// @Success      200   {object}  GreetingResponse
// @Failure      400   {object}  map[string]string
// @Router       /greeting/{name} [get]
func getGreeting(c *gin.Context) {
	name := c.Param("name")
	if name == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "name is required"})
		return
	}

	c.JSON(http.StatusOK, GreetingResponse{
		Message: "Hello, " + name + "!",
	})
}
