package com.pohribnyi.blogSSU.stepdefs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StepDefinitions {

	WebDriver driver;

	private String postTitle;
    private String postDescription;
    private String postContent;

	private final Map<String, String> endpoints = Map.of("Login", "auth/login", "Sig up", "auth/register", "Profile",
			"profile", "Add new post", "posts", "Home", "blog-SSU");
    
	@Before
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Given("I am a logged in user")
	public void i_am_a_logged_in_user() {
		driver.get("http://localhost:8787/blog-SSU/auth/login");
		driver.findElement(By.name("username")).sendKeys("vetka6.30@gmail.com");
		driver.findElement(By.name("password")).sendKeys("1234567");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}

	@When("I click on {string} link on the {string} page")
	public void i_click_on_link_on_the_page(String linkText, String page) {
		driver.findElement(By.linkText(linkText)).click();
	}

	@And("I fill in {string} with {string}")
    public void i_fill_in_with(String fieldName, String value) {
        WebElement field = driver.findElement(By.name(fieldName));
        field.clear();
        field.sendKeys(value);
        switch (fieldName) {
        case "title":
            this.postTitle = value;
            break;
        case "description":
            this.postDescription = value;
            break;
        case "content":
            this.postContent = value;
            break;
    }
    }

    @And("I click on the {string} button")
    public void i_click_on_the_button(String buttonText) {
        WebElement button = driver.findElement(By.xpath("//button[text()='" + buttonText + "']"));
        button.click();
    }

    @Then("I should land on the {string} page")
    public void i_should_land_on_the_page(String expectedPage) {
    	assertTrue(driver.getCurrentUrl().toLowerCase().contains(endpoints.get(expectedPage).toLowerCase()));
    }
 
    @Then("I should see the new blog listing on the Homepage")
    public void i_should_see_the_new_blog_listing_on_the_homepage() {
    	WebElement container = driver.findElement(By.className("post-container"));
        List<WebElement> posts = container.findElements(By.className("post"));

        boolean found = posts.stream()
            .anyMatch(post -> post.getText().contains(postTitle) &&
                              post.getText().contains(postDescription) &&
                              post.getText().contains(postContent));

        assertTrue(found);
        driver.quit();
    }
    
    @Given("I am a registered user")
    public void i_am_a_registered_user() {
    }

	@Given("I navigate to the {string} page")
	public void i_navigate_to_the_page(String expectedPage) {
		i_should_land_on_the_page(expectedPage);
	}

	@Then("I should be successfully logged in")
	public void i_should_be_successfully_logged_in() {
	}

	@Then("I should see {string} and {string} links")
	public void i_should_see_and_links(String string, String string2) {
	}

}
