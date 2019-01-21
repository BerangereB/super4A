package fr.esiea;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Quentin on 15/01/2019
 * @project super4A - PACKAGE_NAME
 */
class MainTest {
	@Test
	void main() {
		Main.main(new String[]{});
		Assertions.assertThat(true).isTrue();
	}
}
