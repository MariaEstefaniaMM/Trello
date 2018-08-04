
function fetching (data, method, url) {
	return new Promise((resolve, reject) => {
        try {
		    switch (method) {
		        case 'GET':
		            {
		                fetch(url, {
		                    method: 'GET',
		                    credentials: 'include',
		                    headers: {
		                            'Content-type': 'application/json'
		                        }
		                })
		                .then((res) => res.json())
		                .then((data) => {
		                    resolve(data);
		                })
		                .catch((err) => {
		                    alert('Error while request...' + err.message);
			            	reject(err);
		                })
		            }
		            break;
		        case 'POST':
		            {
		                let datajson = {
		                    method: 'POST',
		                    body: JSON.stringify(data),
		                    withCredentials: true,
		                    credentials: 'include',
		                    headers: {
		                        'Content-type': 'application/json'
		                    }
		                };
		                fetch(url, datajson)
		                .then((res) => res.json())
		                .then((data) => {
		                    resolve(data);
		                })
		                .catch((err) => {
		                    console.log(err);
			            	reject(err);
		                })
		            }
		            break;
		        case 'DELETE':
		        {
		            let datajson = {
		                method: 'DELETE',
		                body: JSON.stringify(data),
		                withCredentials: true,
		                credentials: 'include',
		                headers: {
		                    'Content-type': 'application/json'
		                }
		            };
		            fetch(url, datajson)
		            .then((res) => res.json())
		            .then((data) => {
		                resolve(data);
		            })
		            .catch((err) => {
		                console.log(err);
		                reject(err);
		            })
		        }
		        break;
		        case 'PUT':
		        {
		            let datajson = {
		                method: 'PUT',
		                body: JSON.stringify(data),
		                withCredentials: true,
		                credentials: 'include',
		                headers: {
		                    'Content-type': 'application/json'
		                }
		            };
		            fetch(url, datajson)
		            .then((res) => res.json())
		            .then((data) => {
		                resolve(data);
		            })
		            .catch((err) => {
		                console.log(err);
		            	reject(err);
		            })
		        }
		        break;
		    }
        } catch (err) {
            console.log('error');
            reject(err);
        }
    });
}

