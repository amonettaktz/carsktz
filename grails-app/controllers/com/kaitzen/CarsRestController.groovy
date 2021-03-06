package com.kaitzen

import com.kaitzen.Car

class CarsRestController {
    static responseFormats = ["json", "xml"]

    def carService

    /**
     * For simple search perform: GET /<baseurl>?from=InitialYear&to=EndYear&make=CarMaker&model=CarModel&max=all&sort=SortingProperty&order=(asc|desc)&offset=Offset
     * Every parameter is optional and no parameters provided returns available max 20 cars.
     * max = ([1-1000] | 'all') : Define maximum results returned.
     * offset: First element returned from query.
     * Sort = ('id'|'year'|'model'|'make'|'plate') : Property used to sort result set.
     * Order = ('asc'|'desc') : Default: 'desc'. Define order for sorting.
     * Any other parameter is ignored, only given parameters are valid.
     * @return selected cars.
     */
    def index() {
        def cars = carService.list(params)
        respond cars
    }

    def show(Integer id){
        def car = carService.show(id)
        if (car)
            respond car, status: 200
        else
            render status: 404, message: "Car #${id} not found"
    }

    def save(Car car) {
        if (car.hasErrors())
            respond car, status: 400
        else {
            def carResponse = carService.save(car.id, car)
            if (savedCar)
                respond carResponse, status: 201
            else
                render status: 500, message: "Car #${car.id} not saved"
        }
    }

    def update(Integer id, Car car) {
        if (car.hasErrors())
            respond car, status: 400
        else
            try {
                respond carService.update(id, car), status: 200
            } catch (CarServiceException e) {
                render status: e.status, message: e.message
            }
    }

    def delete(Integer id) {
        try {
            respond carService.delete(id), body: "Car with ID ${id} deleted", status: 200
        } catch (CarServiceException e) {
            render status: e.status, message: e.message
        }
    }
}
