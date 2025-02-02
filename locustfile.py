from locust import HttpUser, task, between

class BookstoreConsumerUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.headers = {
            "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA4MDE1OTI2ODY1OTY5NjIwODEiLCJuYW1lIjoiU2F1bG8gRGlhcyIsImV4cCI6MTczODQzNzc0NCwiaWF0IjoxNzM4NDM0MTQ0LCJlbWFpbCI6InNhdWxvZGRpbml6QGdtYWlsLmNvbSJ9.Cg7j0KbkxngeS2VJ4zoFWCx_KEtKhb-gr_Ikrm8CDbQ",
            "Content-Type": "application/json"
        }

    @task
    def get_authors_and_books(self):
        self.client.get("/bookstore-consumer-api/authors?books=true", headers=self.headers)
