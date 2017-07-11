var pharmacyApp = angular.module('pharmacyApp', ['ngRoute']);

pharmacyApp.service('pagingSvc', function($location){
	
	this.back = function(search_url, page){
		page = page - 1;
		return $location.path(search_url + page);
	};
	
	this.forward = function(search_url, page){
		page = page + 1;
		return $location.path(search_url + page);
	};
	
	this.first = function(search_url){
		return $location.path(search_url + 0);
	};
	
	this.last = function(search_url, pages){
		lastPage = pages - 1;
		return $location.path(search_url + lastPage);
	};
});

pharmacyApp.controller('medicationsCtrl', function($scope, $http, $location){
	
	$scope.base_url = "/api/medications";
	$scope.medications = [];
		
	var getMedications = function () {
		$http.get($scope.base_url)
			.then(function success(data) {
				$scope.medications = data.data;
			}, function error(data) {
				console.log(data);
			})
		};
		
	getMedications();
	
	$scope.addMedication = function() {
		$location.path("/medications/add");
	};
	
	$scope.editMedication = function(id) {
		$location.path("/medications/edit/" + id);
	};
	
	$scope.deleteMedication = function(id) {
		$http.delete($scope.base_url + "/" + id)
			.then(function success(data){
				getMedications();
			}, function error(data){
				console.log(data);
			})
	};
	
	$scope.findMedication = function() {
		$location.path("/medications/find");
	};
	
	$scope.viewMedication = function(id) {
		$location.path("/medications/view/" + id);
	};
	
});

//==========================================================================================

pharmacyApp.controller('addMedicationCtrl', function($scope, $http, $location){
	
	$scope.medication = {};
	$scope.base_url = "/api/medications";
	
	$scope.add = function(){
		$http.post($scope.base_url, $scope.medication)
		.then(function success(data){
			$location.path("/medications");
		}, function error(data){
			console.log(data);
		});
		
	};
	
});

//==========================================================================================

pharmacyApp.controller('viewMedicationCtrl', function($scope, $http, $routeParams){
	
	$scope.medication = {};
	$scope.base_url = "/api/medications";
	
	var getMedication = function(id){
		$http.get($scope.base_url + "/" + $routeParams.id)
		.then(function success(data){
			$scope.medication = data.data;
		}, function error(data){
			console.log(data);
		});
		
	};
	
	getMedication();
	
});

//==========================================================================================

pharmacyApp.controller('editMedicationCtrl', function($scope, $http, $location, $routeParams){
	
	$scope.medication = {};
	$scope.base_url = "/api/medications/";
	
	var getMedication = function(id){
		$http.get($scope.base_url + "/" + $routeParams.id)
		.then(function success(data){
			$scope.medication = data.data;
		}, function error(data){
			console.log(data);
		});
		
	};
	
	getMedication();
	
	$scope.edit = function(){
		$http.put($scope.base_url + $routeParams.id, $scope.medication)
			.then(function success(data){
				$location.path("/medications");
			}, function error(data){
				console.log(data);
			})
		
	};
	
});

//==========================================================================================

pharmacyApp.controller('findMedicationCtrl', function($scope, $http, $routeParams, $location){
	
	$scope.medicationSearch = {};
	$scope.medications = [];
	$scope.indicator = true;
	
	$scope.find = function(){
		getMedications();
	};
	
	var getMedications = function(){ 
		
		var config = {params: {}};
		
//		config.params.page = $scope.page;
		
		if ($scope.medicationSearch.naziv != "") {
			config.params.naziv = $scope.medicationSearch.naziv;
		}
		if ($scope.medicationSearch.minC != "") {
			config.params.minC = $scope.medicationSearch.minC;
		}
		if ($scope.medicationSearch.maxC != "") {
			config.params.maxC = $scope.medicationSearch.maxC;
		}
		
		$http.get("/api/medications", config)
			.then(function success(data){
				if (data.data != ""){
					$scope.indicator = false;
				} else {
					$scope.indicator = true;
				}
				$scope.medications = data.data;
//				$scope.num_pages = data.headers("pages");
			}, function error(data){
				console.log(data);
			});
		
	};
	
});

//==========================================================================================

pharmacyApp.controller('pharmaciesCtrl', function($scope, $http, $location){
	
	$scope.pharmacies = [];
	
	$scope.base_url = "/api/pharmacies";
	
	var getPharmacies = function(){
		$http.get($scope.base_url)
		.then(function success(data){
			$scope.pharmacies = data.data;
			angular.forEach($scope.pharmacies, function(pharmacy){
				$scope.getMedicationsForPharmacy(pharmacy);
			});
		}, function error(data){
			console.log(data);
		})
	};
	
	getPharmacies();
	
	$scope.getMedicationsForPharmacy = function(pharmacy){
		$http.get("/api/pharmacies/" + pharmacy.id + "/medications")
		.then(function success(data){
			return pharmacy.medications = data.data;
		}, function error(data){
			console.log(data);
		})
	};
	
	$scope.addPharmacy = function(){
		$location.path("/pharmacies/add" );
	};
	
	$scope.viewPharmacy = function(id){
		$location.path("/pharmacies/view/" + id);
	};
	
	$scope.editPharmacy = function(id) {
		$location.path("/pharmacies/edit/" + id);
	};
	
	$scope.deletePharmacy = function(id) {
		$http.delete($scope.base_url + "/" + id)
			.then(function success(data){
				getPharmacies();
			}, function error(data){
				console.log(data);
			})
	};
	
	$scope.findPharmacy = function() {
		$location.path("/pharmacies/find");
	};
	
	$scope.managePharmacy = function(id){
		$location.path("/pharmacies/manage/" + id)
	};
	
});

//==========================================================================================

pharmacyApp.controller('addPharmacyCtrl', function($scope, $http, $location){
	
	$scope.base_url = "/api/pharmacies";
	$scope.pharmacy = {};
	$scope.add = function(){
		$http.post($scope.base_url, $scope.pharmacy)
		.then(function success(data){
			$location.path("/pharmacies");
		}, function error(data){
			console.log(data);
		})
	};
	
});

//==========================================================================================

pharmacyApp.controller('editPharmacyCtrl', function($scope, $http, $location, $routeParams){
	
	$scope.base_url = "/api/pharmacies/";
	$scope.pharmacy = {};
	$scope.pharmacy.medications = [];
	
	
	var getPharmacy = function(){
		$http.get($scope.base_url + $routeParams.id)
		.then(function success(data){
			$scope.pharmacy = data.data;
		}, function error(data){
			console.log(data);
		});
	};
	
	getPharmacy();
	
	var getMedicationsForPharmacy = function(id){
		$http.get("/api/pharmacies/" + $routeParams.id + "/medications")
		.then(function success(data){
			$scope.pharmacy.medications = data.data;
		}, function error(data){
			console.log(data);
		})
	};
	
	getMedicationsForPharmacy();
	
	$scope.edit = function(){
		$http.put($scope.base_url + $routeParams.id, $scope.pharmacy)
		.then(function success(data){
			$location.path("/pharmacies");
		}, function error(data){
			console.log(data);
		});
	};
	
});

//==========================================================================================

pharmacyApp.controller('viewPharmacyCtrl', function($scope, $http, $location, $routeParams){
	
	$scope.base_url = "/api/pharmacies/";
	$scope.pharmacy = {};
	$scope.pharmacy.medications = [];
	
	var getPharmacy = function(){
		$http.get($scope.base_url + $routeParams.id)
		.then(function success(data){
			$scope.pharmacy = data.data;
			angular.forEach($scope.pharmacies, function(pharmacy){
				$scope.getMedicationsForPharmacy(pharmacy);
			});
		}, function error(data){
			console.log(data);
		})
	};
	
	getPharmacy();
	
	var getMedicationsForPharmacy = function(id){
		$http.get("/api/pharmacies/" + $routeParams.id + "/medications")
		.then(function success(data){
			$scope.pharmacy.medications = data.data;
		}, function error(data){
			console.log(data);
		})
	};
	
	getMedicationsForPharmacy();
	
});

//==========================================================================================

pharmacyApp.controller('findPharmacyCtrl', function($scope, $http, $location){
	
	// TO-DO
	
});

//==========================================================================================

pharmacyApp.controller('managePharmacyCtrl', function($scope, $http, $location, $routeParams){
	
	$scope.base_url = "/api/pharmacies/";
	$scope.pharmacy = {};
	$scope.pharmacy.medications = [];
	$scope.availableMedications = [];
	$scope.medications_base_url = "/api/medications";
	
	var getPharmacy = function(){
		$http.get($scope.base_url + $routeParams.id)
		.then(function success(data){
			$scope.pharmacy = data.data;
		}, function error(data){
			console.log(data);
		})
	};
	
	getPharmacy();
	
	var getMedicationsForPharmacy = function(id){
		$http.get("/api/pharmacies/" + $routeParams.id + "/medications")
		.then(function success(data){
			$scope.pharmacy.medications = data.data;
		}, function error(data){
			console.log(data);
		})
	};
	
	getMedicationsForPharmacy();

	var getMedications = function () {
		$http.get($scope.medications_base_url)
		.then(function successCallback(data) {
			$scope.availableMedications = data.data;
		}, function errorCallback(data) {
			console.log(data);
		})
	};
	
	getMedications();
	
	$scope.medicationToAdd = {}; 
	
	$scope.addMedicationToPharmacy = function(idMedication){
		$http.get($scope.medications_base_url + "/" + idMedication)
		.then(function success(data){
			$scope.medicationToAdd = data.data;
			addMedication();
		}, function error(data){
			console.log(data);
		});
		
	};
	
	var addMedication = function() {
		$http.post($scope.base_url + $routeParams.id + "/medications", $scope.medicationToAdd)
		.then(function success(data){
			getMedicationsForPharmacy();
		}, function error(data){
			console.log(data);
		})
		
	};
	
	$scope.removeMedicationFromPharmacy = function(id){
		$http.delete($scope.base_url + $routeParams.id + "/medications/" + id)
		.then( function success(data){
			getMedicationsForPharmacy();
		}, function error(data){
			console.log(data);
		})
	};
	
});

//==========================================================================================

pharmacyApp.config(['$routeProvider', function($routeProvider) {
	
	$routeProvider
	
		.when('/', {
			templateUrl : '/static/app/html/partial/home.html' 
		})
		
		.when('/pharmacies', {
			templateUrl : '/static/app/html/partial/pharmacies.html'
		})
		
		.when('/medications', {
			templateUrl : '/static/app/html/partial/medications.html'
		})
		
		.when('/pharmacies/add', {
			templateUrl : '/static/app/html/partial/add-pharmacy.html'
		})
		
		.when('/medications/add', {
			templateUrl : '/static/app/html/partial/add-medication.html'
		})
		
		.when('/pharmacies/edit/:id', {
			templateUrl : '/static/app/html/partial/edit-pharmacy.html'
		})
		
		.when('/medications/edit/:id', {
			templateUrl : '/static/app/html/partial/edit-medication.html'
		})
		
		.when('/pharmacy/find', {
			templateUrl : '/static/app/html/partial/find-pharmacy.html'
		})
		
		.when('/medications/find', {
			templateUrl : '/static/app/html/partial/find-medication.html'
		})
		
		.when('/pharmacies/view/:id', {
			templateUrl : '/static/app/html/partial/view-pharmacy.html'
		})
		
		.when('/pharmacies/manage/:id', {
			templateUrl : '/static/app/html/partial/manage-medications-pharmacy.html'
		})
		
		.when('/medications/view/:id', {
			templateUrl : '/static/app/html/partial/view-medication.html'
		})
		
		.otherwise({
			redirectTo: '/'
		});

}]);