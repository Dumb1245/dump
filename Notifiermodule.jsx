import React, { useState } from 'react';

function UserForm() {
    const [formData, setFormData] = useState({
        userId: '',
        emailId: '',
        typeOfUser: '',
        typeOf3rdParty: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        // You can send formData to an API or handle it as per your requirements.
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="userId">User ID:</label>
                <input type="text" id="userId" name="userId" value={formData.userId} onChange={handleChange} />
            </div>

            <div>
                <label htmlFor="emailId">Email ID:</label>
                <input type="email" id="emailId" name="emailId" value={formData.emailId} onChange={handleChange} />
            </div>

            <div>
                <label htmlFor="typeOfUser">Type of User:</label>
                <select id="typeOfUser" name="typeOfUser" value={formData.typeOfUser} onChange={handleChange}>
                    <option value="admin">Admin</option>
                    <option value="user">User</option>
                    <option value="guest">Guest</option>
                    // Add other user types if needed.
                </select>
            </div>

            <div>
                <label htmlFor="typeOf3rdParty">Type of 3rd Party:</label>
                <select id="typeOf3rdParty" name="typeOf3rdParty" value={formData.typeOf3rdParty} onChange={handleChange}>
                    <option value="partner">Partner</option>
                    <option value="vendor">Vendor</option>
                    <option value="affiliate">Affiliate</option>
                    // Add other 3rd party types if needed.
                </select>
            </div>

            <div>
                <button type="submit">Submit</button>
            </div>
        </form>
    );
}

export default UserForm;
