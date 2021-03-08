package one.digitalinnovation.stockquotes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockQuotesApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void deveRetornarAoMenosUmaCotacao() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/quotes"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$..quotes").isArray())
				.andExpect(jsonPath("$..quotes[:1].symbol", is(notNullValue())))
				.andExpect(jsonPath("$..quotes[:1].openValue", is(notNullValue())))
				.andExpect(jsonPath("$..quotes[:1].closeValue", is(notNullValue())));
	}

}
