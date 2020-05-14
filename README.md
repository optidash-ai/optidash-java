<p align="center"><a href="https://optidash.ai"><img src="media/logotype.png" alt="Optidash" width="143" height="45"/></a></p>

<p align="center">
Optidash is a modern, AI-powered image optimization and processing API.<br>We will drastically speed-up your websites and save you money on bandwidth and storage.
</p>

---
<p align="center">
<strong>The official Java integration for the Optidash API.</strong><br>
<br>
<img src="https://img.shields.io/maven-central/v/optidash/optidash?style=flat&color=success"/>
<img src="https://img.shields.io/snyk/vulnerabilities/github/optidash-ai/optidash-java?style=flat&color=success"/>
<img src="https://img.shields.io/github/issues-raw/optidash-ai/optidash-java?style=flat&color=success"/>
<img src="https://img.shields.io/twitter/follow/optidashAI?label=Follow%20Us&style=flat&color=success&logo=twitter"/>
</p>

---

### Documentation
See the [Optidash API docs](https://docs.optidash.ai/).

### Quick examples
Optisash API enables you to provide your images for optimization and processing in two ways - by uploading them directly to the API ([Image Upload](https://docs.optidash.ai/requests/image-upload)) or by providing a publicly available image URL ([Image Fetch](https://docs.optidash.ai/requests/image-fetch)).

You may also choose your preferred [response method](https://docs.optidash.ai/introduction#choosing-response-method-and-format) on a per-request basis. By default, the Optidash API will return a [JSON response](https://docs.optidash.ai/responses/json-response-format) with rich metadata pertaining to input and output images. Alternatively, you can use [binary responses](https://docs.optidash.ai/responses/binary-responses). When enabled, the API will respond with a full binary representation of the resulting (output) image. This Java integration exposes two convenience methods for interacting with binary responses: `toFile()` and `toBuffer()`.

#### Image upload
Here is a quick example of uploading a local file for processing. It calls `toJSON()` at a final step and instructs the API to return a JSON response.

```java
package ai.optidash.examples;

// Import dependencies
import static ai.optidash.OperationConfiguration.settings;
import ai.optidash.Optidash;
import ai.optidash.Upload;
import ai.optidash.Response;

public class Example {
    public static void main(String[] args) {

        // Pass your Optidash API Key to the constructor
        Optidash opti = new Optidash("your-api-key");

        // Upload an image for optimization and processing
        // with `upload()` method
        final Upload upload = opti.upload("path/to/input.jpg");

        // Optimize the image
        upload.optimize(
            settings()
                .set("compression", "medium")
        );

        // Resize the image to 100 x 75
        upload.resize(
            settings()
                .set("width", 100)
                .set("height", 75)
        );

        // Automatically enhance the image
        upload.auto(
            settings().set("enhance", true)
        );

        // Adjust sharpness parameter
        upload.adjust(
            settings().set("unsharp", 10)
        );

        try {
            // Finalize the chain with .toJSON()
            final Response response = upload.toJSON();

            // You can access the full JSON metadata with `Response#getMetadata()`
            if (response.isSuccessful()) {
                System.out.println(response.getMetadata().getOutput().get("url"));
            } else {
                System.out.println(response.getMessage());
            }
        } catch (IOException io) {
            // handle IOException
        }
    }
}
```

#### Image fetch
If you already have your source visuals publicly available online, we recommend using Image Fetch by default. That way you only have to send a JSON payload containing image URL and processing steps. This method is also much faster than uploading a full binary representation of the image.

```java
package ai.optidash.examples;

// Import dependencies
import static ai.optidash.OperationConfiguration.settings;
import ai.optidash.Optidash;
import ai.optidash.Fetch;
import ai.optidash.Response;

public class Example {
    public static void main(String[] args) {

        // Pass your Optidash API Key to the constructor
        Optidash opti = new Optidash("your-api-key");

        // Provide a publicly available image URL with `fetch()` method
        final Fetch fetch = opti.fetch("https://www.website.com/image.jpg");

        // Optimize the image
        fetch.optimize(
            settings()
                .set("compression", "medium")
        );

        // Apply Gaussian blur
        fetch.filter(
            settings()
                .set("blur", settings()
                    .set("mode", "gaussian")
                    .set("value", 10)
                )
        );

        // Use PNG as output format
        fetch.output(
            settings().set("format", "png")
        );

        try {
            // Finalize the chain with .toFile()
            final Response response = fetch.toFile("path/to/output.jpg");

            // You can access the full JSON metadata with `Response#getMetadata()`
            if (response.isSuccessful()) {
                System.out.println(response.getMetadata().getOutput().get("url"));
            } else {
                System.out.println(response.getMessage());
            }
        } catch (IOException io) {
            // handle IOException
        }
    }
}
```

### License
This software is distributed under the MIT License. See the [LICENSE](LICENSE) file for more information.