import os

import requests


# API Pull public gateway. Override APIPULL_BASE_URL for QA, staging, or private deployments.
BASE_URL = os.getenv("APIPULL_BASE_URL", "https://www.apipull.com")

# Keep credentials outside source control; inject the API key from your runtime environment.
API_KEY = os.getenv("APIPULL_API_KEY", "")

# Replace these placeholder paths with the API Pull endpoints selected from product documentation.
GET_PATH = os.getenv("APIPULL_GET_PATH", "/api/example/status")
POST_PATH = os.getenv("APIPULL_POST_PATH", "/api/example/verify")


def headers():
    """Build API Pull request headers, including optional bearer-token authentication."""
    values = {
        "Accept": "application/json",
        "Content-Type": "application/json",
    }
    if API_KEY:
        values["Authorization"] = f"Bearer {API_KEY}"
    return values


def main():
    # GET request for status, lookup, search, or read-only API operations.
    get_response = requests.get(
        f"{BASE_URL}{GET_PATH}",
        headers=headers(),
        timeout=30,
    )
    print("GET status:", get_response.status_code)
    print(get_response.text)

    # POST request for verification, enrichment, onboarding, or submitted business data.
    post_response = requests.post(
        f"{BASE_URL}{POST_PATH}",
        headers=headers(),
        json={
            "requestId": "demo-001",
            "name": "API Pull Demo",
        },
        timeout=30,
    )
    print("POST status:", post_response.status_code)
    print(post_response.text)


if __name__ == "__main__":
    main()
