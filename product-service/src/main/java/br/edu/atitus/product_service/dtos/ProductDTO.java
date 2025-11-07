package br.edu.atitus.product_service.dtos;

public record ProductDTO(
		String description,
		String brand,
		String model,
		String currency,
		String price,
		String imageUrl) {

}
