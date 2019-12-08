'use strict';
const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {
			historyNumbers: [], 
			hotColdNumbers: [],
			numbers: [],
			tirages: [],
		};
	}
	
	componentDidMount() {
		this.loadFromServer();
	}
	
	loadFromServer = () => {
		fetch("/numero/list", {
			headers: {
				'Accept': 'application/json'
			},
		}).then(response =>
			response.json()
		).then(numbers => {
			this.setState({
				numbers
			})
		});
		fetch("/tirage/list", {
			headers: {
				'Accept': 'application/json'
			},
		}).then(response =>
			response.json()
		).then(historyNumbers => {
			this.setState({
				historyNumbers
			})
		});
		fetch("/tirage/list-all", {
			headers: {
				'Accept': 'application/json'
			},
		}).then(response =>
			response.json()
		).then(tirages => {
			this.setState({
				tirages
			})
		});
		fetch("/tirage/list-hot-cold", {
			headers: {
				'Accept': 'application/json'
			},
		}).then(response =>
			response.json()
		).then(hotColdNumbers => {
			this.setState({
				hotColdNumbers
			})
			this.dessinerGraphique(hotColdNumbers);
		});
	}
	
	deleteTirage = (tirage) => {
		return fetch("/tirage/delete", {
			headers: {
				'Accept': 'application/json',
		        'Content-Type': 'application/json'
			},
			method: "POST",
			body: JSON.stringify(tirage)
		}).then(response => {
			if(response.status === 200) {
				this.loadFromServer();
			} else {
			}
		});
	};
	
	addNumber = (number) => {
		return fetch("/tirage/add", {
			headers: {
				'Accept': 'application/json',
		        'Content-Type': 'application/json'
			},
			method: "POST",
			body: JSON.stringify(number)
		}).then(response => {
			if(response.status === 200) {
				this.loadFromServer();
			} else {
			}
		});
	};
	
	render() {
		const historyNumbers = this.state.historyNumbers.map((number, index) => {
			return <div key={number.id} className={`roulette__history-box selectable-${number.couleur}`}>
				<div className="roulette__history-box-delete"><a onClick={() => {this.deleteTirage(number)}}>X</a></div>
				<div className="roulette__history-number">{number.valeur}</div>
			</div>
		});
		
		const hotNumbers = this.state.hotColdNumbers.slice(32, 37).map((number, index) => {
			return <div key={index} className={`roulette__hot-box selectable-${number.couleur}`}>
				<div className="roulette__hot-number">{number.valeur}</div>
			</div>
		});
		
		const coldNumbers = this.state.hotColdNumbers.slice(0, 5).map((number, index) => {
			return <div key={index} className={`roulette__cold-box selectable-${number.couleur}`}>
				<div className="roulette__cold-number">{number.valeur}</div>
			</div>
		});
		
		const possibilities = this.state.numbers.map((number, index) => {
			return <a key={index} onClick={() => {
				this.addNumber(number)
			}}>	<div className={`selectable selectable-${number.couleur}`}>
					<div className="selectable-number">{number.valeur}</div>
				</div>
			</a>
		});
		
		return (
				<section className="wrapper">
					<div className="content">
						<div className="roulette__content">
							{possibilities}
							<canvas id="camembert" width="500" height="400"></canvas>
						</div>
						<div className="slide roulette__hot">
							{hotNumbers}
						</div>
						<div className="slide roulette__cold">
							{coldNumbers}
						</div>
						<div className="roulette__stats">
							<div className="roulette__stats-oddEven">
								<p className="roulette__stats-even">Pair : {this.getEvenPercentage(this.state.tirages)}%</p>
								<p className="roulette__stats-odd">Impair : {this.getOddPercentage(this.state.tirages)}%</p>
							</div>
							<div className="roulette__stats-redBlack">
								<p className="roulette__stats-green">Vert : {this.getGreenPercentage(this.state.tirages)}%</p>
								<p className="roulette__stats-red">Rouge : {this.getRedPercentage(this.state.tirages)}%</p>
								<p className="roulette__stats-black">Noir : {this.getBlackPercentage(this.state.tirages)}%</p>
							</div>
							<div className="roulette__stats-manquePasse">
								<p className="roulette__stats-manque">1-18 : {this.getManquePercentage(this.state.tirages)}%</p>
								<p className="roulette__stats-passe">19-36 : {this.getPassePercentage(this.state.tirages)}%</p>
							</div>
						</div>
					</div>
					<div className="footer">
						<div className="roulette__history">
							{historyNumbers}
						</div>
					</div>
				</section>
		)
	}
	
	getOddPercentage(tirages) {
		let nbImpairs = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].valeur % 2 == 1) {
				nbImpairs++;
			}
		}
		let percentage = (nbImpairs * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getEvenPercentage(tirages) {
		let nbPairs = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].valeur % 2 == 0) {
				nbPairs++;
			}
		}
		let percentage = (nbPairs * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getGreenPercentage(tirages) {
		let nbGreen = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur == "0") {
				nbGreen++;
			}
		}
		let percentage = (nbGreen * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getRedPercentage(tirages) {
		let nbRed = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].couleur == "ROUGE") {
				nbRed++;
			}
		}
		let percentage = (nbRed * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getBlackPercentage(tirages) {
		let nbBlack = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].couleur == "NOIR") {
				nbBlack++;
			}
		}
		let percentage = (nbBlack * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getManquePercentage(tirages) {
		let nbManque = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].valeur < 19) {
				nbManque++;
			}
		}
		let percentage = (nbManque * 100) / tirages.length;
		return percentage.toFixed(2);
	}
	
	getPassePercentage(tirages) {
		let nbPasse = 0;
		for(var i = 0; i<tirages.length; i++) {
			if(tirages[i].valeur != "0" && tirages[i].valeur > 18) {
				nbPasse++;
			}
		}
		let percentage = (nbPasse * 100) / tirages.length;
		return percentage.toFixed(2);
	}
}

ReactDOM.render((
	<App />
), document.getElementById('react'));

