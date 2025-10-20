package com.example.data.mapper

import com.example.data.model.ListingResponseDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class AllListingsMapperTest {

    private val mapper = AllListingsMapper()

    @Test
    fun `toListingBO maps all non-null fields correctly`() {
        val dto = ListingResponseDto(
            id = 10,
            city = "Bucharest",
            propertyType = "Maison - Villa",
            price = 350.00,
            bedrooms = 3,
            area = 120.5,
            url = "https://img.example.com/10.jpg",
            professional = "Agency X",
            offerType = 1,
            rooms = 5
        )

        val bo = mapper.toListingBO(dto)

        assertEquals(10, bo.id)
        assertEquals("Bucharest", bo.city)
        assertEquals("Maison - Villa", bo.propertyType)
        assertEquals(expected = 350.0, actual = bo.price, 0.0)
        assertEquals(3, bo.bedrooms)
        assertEquals(120.5, bo.area, 0.0)
        assertEquals("https://img.example.com/10.jpg", bo.imageUrl)
        assertEquals("Agency X", bo.professional)
        assertEquals(5, bo.rooms)
    }

    @Test
    fun `toListingBO applies defaults when nullable fields are null`() {
        val dto = ListingResponseDto(
            id = 1,
            city = "Cluj",
            propertyType = "Apartment",
            price = 100.000,
            bedrooms = null,
            area = null,
            url = "https://img.example.com/1.jpg",
            professional = "GSL OWNERS",
            offerType = 2,
            rooms = null
        )

        val bo = mapper.toListingBO(dto)

        assertEquals(0, bo.bedrooms)
        assertEquals(0.0, bo.area, 0.0)
        assertEquals(0, bo.rooms)
        assertEquals("Cluj", bo.city)
        assertEquals("https://img.example.com/1.jpg", bo.imageUrl)
    }

    @Test
    fun `toAllListings maps list and preserves order`() {
        val dto1 = ListingResponseDto(
            id = 1, city = "Iasi", propertyType = "Studio", price = 60.000,
            bedrooms = 1, area = 30.0, url = "u1", professional = "GSL", offerType = 1, rooms = 1
        )
        val dto2 = ListingResponseDto(
            id = 2,
            city = "Timisoara",
            propertyType = "House",
            price = 200.000,
            bedrooms = null,
            area = null,
            url = "u2",
            professional = "GSL2",
            offerType = 1,
            rooms = null
        )

        val result = mapper.toAllListings(listOf(dto1, dto2))

        assertEquals(2, result.size)
        assertEquals(1, result[0].id)
        assertEquals(2, result[1].id)
        assertEquals(0, result[1].bedrooms)
        assertEquals(0.0, result[1].area, 0.0)
        assertEquals(0, result[1].rooms)
    }

    @Test
    fun `toAllListings with empty list returns empty`() {
        val result = mapper.toAllListings(emptyList())
        assertTrue(result.isEmpty())
    }
}
