
const Users = ({users}) => {

    if (!users && !localStorage.getItem('jwt'))
        return (<div>You should be authorized to see this page</div>)

    if (!users)
        return (<p>Loading...</p>);

    return (
        <div className="datatable">
            <div className="caption">Users</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Login</th>
                    <th>Creation Time</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user, index) => (
                    <tr key={index}>
                        <td>{user.id}</td>
                        <td>{user.login}</td>
                        <td>{new Date(user.creationTime).toString()}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Users;