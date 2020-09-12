package com.zeylin.pricetracker;

import com.zeylin.pricetracker.dao.PriceDAO;
import com.zeylin.pricetracker.dto.AddPriceRequest;
import com.zeylin.pricetracker.dto.PriceDto;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.dto.UpdatePriceRequest;
import com.zeylin.pricetracker.exceptions.NotFoundException;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceTrackerApplicationTests {

	@ClassRule
	public static PostgreSQLContainer<PostgresContainer> postgreSQLContainer = PostgresContainer.getInstance();

	@Autowired
	protected Flyway flyway;

	@Autowired
	protected PriceDAO priceDAO;

	@Before
	public void init() {
		flyway.clean();
		flyway.migrate();
	}

	@Test
	public void loadingPrice() {
		// given
		Integer id = priceDAO.addPrice(generateAddPriceRequest(2, 2000));

		// when
		PriceDto priceDto = priceDAO.loadPrice(id);

		// then
		assertEquals(id, priceDto.getId());
		assertEquals(Integer.valueOf(2), priceDto.getItemId());
		assertEquals(Integer.valueOf(1), priceDto.getLocationId());
		assertEquals((Integer) 2000, priceDto.getAmount());
		assertEquals(LocalDate.now(), priceDto.getDate());
		assertEquals(false, priceDto.getIsDeleted());
	}

	@Test
	public void updatingPrice() {
		// given
		Integer id = priceDAO.addPrice(generateAddPriceRequest(3, 3000));

		UpdatePriceRequest request = new UpdatePriceRequest();
		request.setId(id);
		request.setItemId(2);
		request.setLocationId(2);
		request.setAmount(2200);
		request.setDate(LocalDate.now().plusDays(1));
		request.setCityId(2);

		// when
		priceDAO.updatePrice(request);

		// then
		PriceDto priceDto = priceDAO.loadPrice(id);
		assertEquals(id, priceDto.getId());
		assertEquals(Integer.valueOf(2), priceDto.getItemId());
		assertEquals(Integer.valueOf(2), priceDto.getLocationId());
		assertEquals((Integer) 2200, priceDto.getAmount());
		assertEquals(LocalDate.now().plusDays(1), priceDto.getDate());
		assertEquals(false, priceDto.getIsDeleted());
	}

	@Test
	public void listingPrices() {
		// given
		priceDAO.addPrice(generateAddPriceRequest(1, 1000));

		// when
		List<PriceListDto> list = priceDAO.list();

		// then
		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	public void deletingPrice() {
		// given
		Integer id = priceDAO.addPrice(generateAddPriceRequest(4, 4000));
		List<PriceListDto> deletedListBefore = priceDAO.listDeleted();
		assertThat(deletedListBefore.size()).isEqualTo(0);

		List<PriceListDto> activeListBefore = priceDAO.list();
		assertThat(activeListBefore.size()).isEqualTo(1);

		// when
		priceDAO.delete(id);

		// then
		List<PriceListDto> deletedListAfter = priceDAO.listDeleted();
		assertThat(deletedListAfter.size()).isEqualTo(1);

		List<PriceListDto> activeListAfter = priceDAO.list();
		assertThat(activeListAfter.size()).isEqualTo(0);
	}

	@Test
	public void restoringPrice() {
		// given
		Integer id = priceDAO.addPrice(generateAddPriceRequest(5, 5000));
		priceDAO.delete(id);

		PriceDto priceDto = priceDAO.loadPrice(id);
		assertEquals(id, priceDto.getId());
		assertEquals(true, priceDto.getIsDeleted());

		// when
		priceDAO.restore(id);

		// then
		PriceDto dto = priceDAO.loadPrice(id);
		assertEquals(id, dto.getId());
		assertEquals(false, dto.getIsDeleted());
	}

	@Test(expected = NotFoundException.class)
	public void permanentlyDeletingPrice() {
		// given
		Integer id = priceDAO.addPrice(generateAddPriceRequest(6, 6000));
		priceDAO.delete(id);

		// when
		priceDAO.deletePermanently(id);

		// then
		priceDAO.loadPrice(id);
	}

	private AddPriceRequest generateAddPriceRequest(int itemId, int amount) {
		AddPriceRequest request = new AddPriceRequest();
		request.setItemId(itemId);
		request.setLocationId(1);
		request.setAmount(amount);
		request.setDate(LocalDate.now());
		request.setCityId(1);
		return request;
	}

}
