import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.mail.polis.dao.person.IPersonService
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.person.PersonService
import ru.mail.polis.metro.Metro

class PersonServiceTest {
    private val personService: IPersonService = PersonService.getInstance()

    @Test
    fun personShouldBeAddedSuccessful() {
        runBlocking {
            val person = createTestPerson()

            val addedPerson = personService.addPerson(person)
            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertNotNull(gotPerson)
            Assert.assertEquals(addedPerson, gotPerson)

            personService.deletePersonByEmail(addedPerson.email)
        }
    }

    @Test
    fun deletingShouldBeSuccessful() {
        runBlocking {
            val person = createTestPerson()

            val addedPerson = personService.addPerson(person)
            personService.deletePersonByEmail(addedPerson.email)

            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertNull(gotPerson)
        }
    }

    @Test
    fun updatingShouldBeSuccessful() {
        runBlocking {
            val person = createTestPerson()
            val updatedPerson = createUpdatedPerson(person)

            val addedPerson = personService.addPerson(person)
            personService.updatePerson(updatedPerson)
            val gotPerson = personService.findByEmail(addedPerson.email)

            Assert.assertEquals(gotPerson, updatedPerson)

            personService.deletePersonByEmail(addedPerson.email)
        }
    }

    private fun createTestPerson(): PersonED {
        return PersonED.Builder.createBuilder()
            .email("test@test.test")
            .photo("test")
            .name("test")
            .age(0)
            .mark(0)
            .tags(listOf(1L))
            .metro(Metro.PARNASSUS)
            .money(0L, 0L)
            .rooms(listOf("test"))
            .description("test")
            .build()
    }

    private fun createUpdatedPerson(oldPerson: PersonED): PersonED {
        return PersonED.Builder.createBuilder()
            .email(oldPerson.email)
            .photo(oldPerson.photo)
            .name(oldPerson.name)
            .age(oldPerson.age)
            .mark(oldPerson.mark)
            .tags(oldPerson.tags)
            .metro(oldPerson.metro)
            .money(oldPerson.moneyFrom, oldPerson.moneyTo)
            .rooms(oldPerson.rooms)
            .description(oldPerson.description)
            .build()
    }
}
